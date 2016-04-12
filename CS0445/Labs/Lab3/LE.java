public class LE
{
  private Comparable data;
  private LE next;

  public LE()
  {
    this( null, null );
  }

  public LE(Comparable data)
  {
    this( data, null );
  }

  public LE(Comparable data, LE next)
  {
    setData( data );
    setNext( next );
  }

  public Comparable getData()
  {
    return data;
  }

  public LE getNext()
  {
    return next;
  }

  public void setData(Comparable data)
  {
     this.data = data;
  }

  public void setNext(LE next)
  {
    this.next = next;
  }
} //EOF
