import akka.actor._

object SistemaDeEstoque {
  case object MensagemLoja 
  case object MensagemEstoque
  case object AbriuLoja
  case object ProximoCliente
  
  
  class Estoque extends Actor {
    def receive = {
      case MensagemLoja =>  // Recebe o pedido de produto da loja e demora um tempo até enviar os produtos requisitados 
        Thread.sleep(15000) // simboliza a demora da chegada dos produtos na loja
        sender ! MensagemEstoque
    }
  }
  
  class Loja (estoque: ActorRef, nome : String) extends Actor {
    var numProdutos : Int  = 100 // Mostra quantos produtos têm na loja
    
    def receive = {
      case l: List[Int] => // Recebe uma Lista com a quantidade de cada um dos 4 produtos
        println("Atendendo Cliente na " + nome + "  ...")
        if (l.foldLeft(0)(_ + _) < 12) // Os if's encadeados selecionam o tempo gasto de atendimento aos clientes, caso tenha mais produtos, maior será a demora
          Thread.sleep(3000)
        else if(l.foldLeft(0)(_ + _) < 23)
          Thread.sleep(6000)
         else
           Thread.sleep(9000)
        this.setNumProdutos(this.getNumProdutos() - l.foldLeft(0)(_ + _)) // Vai diminuindo a quantiade de produtos da loja conforme a lista de pedidos
        if (this.getNumProdutos() <= 10){ // verifica se é necessário pedir mais produtos ao estoque
          println("Esperando produtos do estoque para a " + nome + " ...")
          estoque ! MensagemLoja // solicita mais produtos ao estoque
        }
        /*else if (this.getNumProdutos() <= 10){
          while(this.getNumProdutos() <= 10){
            println("Esperando produtos do estoque para a " + nome + " ...")
            Thread.sleep(2500)
          }
        }*/
        sender ! ProximoCliente // Manda uma mensagem ao ator consumidor dizendo que a loja está preparada pra receber o próximo cliente
            
      case MensagemEstoque => // Mensagem recebida pelo estoque de que os produtos chegaram
        println("Os produtos da " + nome + " chegaram!")
        this.setNumProdutos(100)
    }
    
    // Set e get para manter o encapsulamento da variável numProdutos
    
    def setNumProdutos(n : Int) : Unit = {
      this.numProdutos = n
    }
    
    def getNumProdutos() : Int = {
      this.numProdutos
    }
    
  }
  
  class Consumidor(loja: ActorRef, nomeLoja : String) extends Actor {
    var senha : Int = 1 // Senha de atendimento do cliente
    def receive = {
      case AbriuLoja => // O programa começa ao abrir as lojas
        println("Senha para atendimento na " + nomeLoja + " : "+ this.getSenha())
        loja ! List(0,1,2,3) // envia a lista de pedidos para a loja
  
      case ProximoCliente =>
        println("Senha " + this.getSenha() + " foi atendida na " + nomeLoja)
        this.setSenha(this.getSenha() + 1)
        Thread.sleep(2500) // Aguarda o próximo cliente chegar
        println("Senha para atendimento na " + nomeLoja + " : "+ this.getSenha())
        loja ! List(((Math.random()*100).toInt) % 9, ((Math.random()*100).toInt) % 9, ((Math.random()*100).toInt) % 9, ((Math.random()*100).toInt) % 9 ) // Gera pedidos de produtos com quantidades aleatórias, para que haja uma diferença de pedidos entre as lojas 
    }
    
    // Set e get para a variável senha
    
    def setSenha(s : Int) : Unit = {
      this.senha = s
    }
    
    def getSenha() : Int = {
      this.senha
    }
  }
  
  def main (args : Array[String]) : Unit = {
    val system = ActorSystem("System")
    val estoque = system.actorOf(Props[Estoque], name = "estoque")
    val loja1 = system.actorOf(Props(new Loja(estoque, "Loja1")),name = "loja1")
    val loja2 = system.actorOf(Props(new Loja(estoque, "Loja2")),name = "loja2")
    val consumidor1 = system.actorOf(Props(new Consumidor(loja1, "Loja1")), name = "consumidor1")
    val consumidor2 = system.actorOf(Props(new Consumidor(loja2, "Loja2")), name = "consumidor2")
    consumidor1 ! AbriuLoja
    consumidor2 ! AbriuLoja
   }
}