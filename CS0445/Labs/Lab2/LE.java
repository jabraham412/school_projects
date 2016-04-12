public class LE
{
  private String data;
  private LE next;

  public LE()
  {
    this( null, null );
  }

  public LE(String data)
  {
    this( data, null );
  }

  public LE(String data, LE next)
  {
    setData( data );
    setNext( next );
  }

  public String getData()
  {
    return data;
  }

  public LE getNext()
  {
    return next;
  }

  public void setData(String data)
  {
     this.data = data;
  }

  public void setNext(LE next)
  {
    this.next = next;
  }
} //EOF
