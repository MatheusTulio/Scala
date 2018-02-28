
object ProjetoParadigmas {
  case class Lista(l : List)
  
  class Estoque extends Actor {
    def receive = {
      
    }
  }
  
  class Loja extends Actor {
    private var produto1 : int = 20
    private var produto2_1 : int = 20
    private var produto2_2 : int = 20
    private var produto3 : int = 20
    
    def receive = {
      case l : List(_, (_,_), _ , _) =>
        this.setProduto1(this.getProduto1() - l[0])
        this.set)roduto3(this.getProduto3() - l[2])
        if (l[1]._1 == "Primeira marca")
          this.setProduto2_1(this.getProduto2_1() - l[1]._2)
        else
            this.setProduto2_2(this.getProduto2_2() - l[1]._2)
        println("Pedido " + l[3] + " atendido")
      
      case l : List( _, (_,_), _) =>
        this.setProduto1(this.getProduto1() - l[0])
        if (l[1]._1 == "Primeira marca")
          this.setProduto2_1(this.getProduto2_1()  - l[1]._2)
        else
            this.setProduto2_2(this.getProduto2_2() - l[1]._2)
        println("Pedido " + l[2] + " atendido")
      
      case l : List((_,_), _, _) =>
        this.set)roduto3(this.getProduto3()  - l[1])
        if (l[0]._1 == "Primeira marca")
          this.setProduto2_1(this.getProduto2_1() - l[0]._2)
        else
            this.setProduto2_2(this.getProduto2_2() - l[0]._2)
        println("Pedido " + l[2] + " atendido")
        
      case l : List(_, _ , _) =>
        this.setProduto1(this.getProduto1() - l[0])
        this.set)roduto3(this.getProduto3() - l[1])
        println("Pedido " + l[2] + " atendido")
        
      case l : List[Int] =>
        this.setProduto1(this.getProduto1() - l.head)
        println("Pedido " + l.tail + " atendido")
        
      case l : List[Double] =>
        this.setProduto3(this.getProduto3() - l.head)
        println("Pedido " + l.tail + " atendido")
        
      case l : List((_,_), _) =>
        if (l[0]._1 == "Primeira marca")
          this.setProduto2_1(this.getProduto2_1() - l.head._2)
        else
            this.setProduto2_2(this.getProduto2_2() - l.head._2)
        println("Pedido " + l.tail + " atendido")
    }
    
    def setProduto1(n : Int) : Unit = {
      this.produto1 = n
    }
    
    def setProduto2_1(n : Int) : Unit = {
      this.produto2_1 = n
    }
    
    def setProduto2_2(n : Int) : Unit = {
      this.produto2_2 = n
    }
    
    def setProduto3(n : Int) : Unit = {
      this.produto3 = n
    }
    
    def getProduto () : Int = {
      this.produto1
    }
    
    def getProduto () : Int = {
      this.produto2_1
    }
    
    def getProduto () : Int = {
      this.produto2_2
    }
    
    def getProduto () : Int = {
      this.produto3
    }
    
    
  }
  
  class Consumidor extends Actor {
    def receive = {
      
    }
  }
  
  def main (args : Array[String]) : Unit = {
    val system = ActorSystem("System")
    val estoque = system.actorOf(Props[Estoque])
    val consumidor = system.actorOf(Props[Consumidor])
    val loja1 = system.actorOf(Props[Loja])
    val loja2 = system.actorOf(Props[Loja])
  }
}