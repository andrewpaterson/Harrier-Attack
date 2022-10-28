package net.engine;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayExtended<E> extends ArrayList<E>
{
  public ArrayExtended(int initialCapacity)
  {
    super(initialCapacity);
  }

  public ArrayExtended()
  {
  }

  public ArrayExtended(Collection c)
  {
    super(c);
  }

  public E get(int index)
  {
    if ((index < 0) || (index >= size()))
    {
      return null;
    }
    return super.get(index);
  }

  public E set(int index, E element)
  {
    if (index >= size())
    {
      int num = (index - size()) + 1;
      for (int i = 0; i < num; i++)
      {
        add(null);
      }
    }
    return super.set(index, element);
  }
}
