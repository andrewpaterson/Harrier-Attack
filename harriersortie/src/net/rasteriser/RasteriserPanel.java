package net.rasteriser;

import net.engine.GamePanel;
import net.engine.graphics.Sprite;
import net.engine.math.Float2;
import net.engine.math.Float3;
import net.engine.math.Float4x4;

import java.awt.*;

/**
 *
 */
public class RasteriserPanel extends GamePanel
{
  public Float4x4 transform;

  public RasteriserPanel()
  {
    super(false, 640, 480);
    transform = new Float4x4();
  }

  protected void initialise()
  {
    Sprite controlSprite = new Sprite(this, 0, 0);
    controlSprite.setControl(new RasteriseControl(controlSprite));
  }

  public void resizedBuffer()
  {
  }

  protected void update()
  {
    super.update();
  }

  public void preRender()
  {
    backBuffer.setColor(Color.YELLOW);
    backBuffer.fillRect(0, 0, 640, 480);
  }

  public void postRender()
  {
    drawTri(new Float3(-0.3f, -0.3f, -0.3f),
            new Float3(0.3f, -0.3f, -0.3f),
            new Float3(-0.3f, 0.3f, -0.3f));
    drawTri(new Float3(0.3f, -0.3f, -0.3f),
            new Float3(0.3f, 0.3f, -0.3f),
            new Float3(-0.3f, 0.3f, -0.3f));

    drawTri(new Float3(0.3f, -0.3f, -0.3f),
            new Float3(-0.3f, -0.3f, -0.3f),
            new Float3(-0.3f, -0.3f, 0.3f));
    drawTri(new Float3(0.3f, -0.3f, 0.3f),
            new Float3(0.3f, -0.3f, -0.3f),
            new Float3(-0.3f, -0.3f, 0.3f));

    drawTri(new Float3(-0.3f, -0.3f, 0.3f),
            new Float3(-0.3f, -0.3f, -0.3f),
            new Float3(-0.3f, 0.3f, -0.3f));
    drawTri(new Float3(-0.3f, 0.3f, 0.3f),
            new Float3(-0.3f, -0.3f, 0.3f),
            new Float3(-0.3f, 0.3f, -0.3f));

    drawTri(new Float3(0.3f, -0.3f, 0.3f),
            new Float3(-0.3f, -0.3f, 0.3f),
            new Float3(-0.3f, 0.3f, 0.3f));
    drawTri(new Float3(0.3f, 0.3f, 0.3f),
            new Float3(0.3f, -0.3f, 0.3f),
            new Float3(-0.3f, 0.3f, 0.3f));

    drawTri(new Float3(-0.3f, 0.3f, -0.3f),
            new Float3(0.3f, 0.3f, -0.3f),
            new Float3(-0.3f, 0.3f, 0.3f));
    drawTri(new Float3(0.3f, 0.3f, -0.3f),
            new Float3(0.3f, 0.3f, 0.3f),
            new Float3(-0.3f, 0.3f, 0.3f));

    drawTri(new Float3(0.3f, -0.3f, -0.3f),
            new Float3(0.3f, -0.3f, 0.3f),
            new Float3(0.3f, 0.3f, -0.3f));
    drawTri(new Float3(0.3f, -0.3f, 0.3f),
            new Float3(0.3f, 0.3f, 0.3f),
            new Float3(0.3f, 0.3f, -0.3f));
  }

  private void drawTri(Float3 p0, Float3 p1, Float3 p2)
  {
    Float3 ps0 = new Float3(transform.multiply(p0));
    Float3 ps1 = new Float3(transform.multiply(p1));
    Float3 ps2 = new Float3(transform.multiply(p2));

    ps0.x = ps0.x / (-ps0.z / 2 + 1.0f);
    ps0.y = ps0.y / (-ps0.z / 2 + 1.0f);

    ps1.x = ps1.x / (-ps1.z / 2 + 1.0f);
    ps1.y = ps1.y / (-ps1.z / 2 + 1.0f);

    ps2.x = ps2.x / (-ps2.z / 2 + 1.0f);
    ps2.y = ps2.y / (-ps2.z / 2 + 1.0f);

    intStyle(toScreenSpace(ps0), toScreenSpace(ps1), toScreenSpace(ps2));
  }

  private Float2 toScreenSpace(Float3 ps)
  {
    return new Float2(ps.x = ps.x * 320 + 320, ps.y = ps.y * 240 + 240);
  }

  private void naiveStyle(Float2 p0, Float2 p1, Float2 p2)
  {
    float x;
    float y;
    float d012 = denominator(p0, p1, p2);
    float d120 = denominator(p1, p2, p0);
    float d201 = denominator(p2, p0, p1);

    for (y = 0; y <= 480; y += 1.0)
    {
      for (x = 0; x <= 640; x += 1.0)
      {
        float alpha = numerator(p1, p2, x, y) / d012;
        float beta = numerator(p2, p0, x, y) / d120;
        float gamma = numerator(p0, p1, x, y) / d201;

        if (((alpha >= 0.0f) && (alpha <= 1.0f)) &&
                ((beta >= 0.0f) && (beta <= 1.0f)) &&
                ((gamma >= 0.0f) && (gamma <= 1.0f)))
        {
          backBuffer.setColor(new Color(alpha, beta, gamma));
          backBuffer.drawRect((int) x, (int) y, 0, 0);
        }
      }
    }
  }

  private void naiveStyle2(Float2 p0, Float2 p1, Float2 p2)
  {
    float x;
    float y;
    float d012 = denominator(p0, p1, p2);
    float d120 = denominator(p1, p2, p0);
    float d201 = denominator(p2, p0, p1);

    for (y = 0; y <= 480; y += 1.0)
    {
      for (x = 0; x <= 640; x += 1.0)
      {
        float alpha = numerator(p1, p2, x, y) / d012;
        float beta = numerator(p2, p0, x, y) / d120;
        float gamma = numerator(p0, p1, x, y) / d201;

        if (((alpha >= 0.0f)) &&
                ((beta >= 0.0f)) &&
                ((gamma >= 0.0f)))
        {
          backBuffer.setColor(new Color(alpha, beta, gamma));
          backBuffer.drawRect((int) x, (int) y, 0, 0);
        }
      }
    }
  }

  private float numerator(Float2 p1, Float2 p2, float x, float y)
  {
    return (((p1.y - p2.y) * x) + ((p2.x - p1.x) * y) + (p1.x * p2.y) - (p2.x * p1.y));
  }

  private float denominator(Float2 p0, Float2 p1, Float2 p2)
  {
    return numerator(p1, p2, p0.x, p0.y);
  }

  private float max(float z0, float z1, float z2)
  {
    if ((z0 >= z1) && (z0 >= z2))
    {
      return z0;
    }
    else if (z1 >= z2)
    {
      return z1;
    }
    else
    {
      return z2;
    }
  }

  private float min(float z0, float z1, float z2)
  {
    if ((z0 <= z1) && (z0 <= z2))
    {
      return z0;
    }
    else if (z1 <= z2)
    {
      return z1;
    }
    else
    {
      return z2;
    }
  }

  private int max(int y0, int y1, int y2)
  {
    if ((y0 >= y1) && (y0 >= y2))
    {
      return y0;
    }
    else if (y1 >= y2)
    {
      return y1;
    }
    else
    {
      return y2;
    }
  }

  private int min(int y0, int y1, int y2)
  {
    if ((y0 <= y1) && (y0 <= y2))
    {
      return y0;
    }
    else if (y1 <= y2)
    {
      return y1;
    }
    else
    {
      return y2;
    }
  }

  void intStyle(Float2 v1, Float2 v2, Float2 v3)
  {
    // 28.4 fixed-point coordinates
    int Y1 = iround(16.0f * v1.y);
    int Y2 = iround(16.0f * v2.y);
    int Y3 = iround(16.0f * v3.y);

    int X1 = iround(16.0f * v1.x);
    int X2 = iround(16.0f * v2.x);
    int X3 = iround(16.0f * v3.x);

    // Deltas
    int DX12 = X1 - X2;
    int DX23 = X2 - X3;
    int DX31 = X3 - X1;

    int DY12 = Y1 - Y2;
    int DY23 = Y2 - Y3;
    int DY31 = Y3 - Y1;

    // Fixed-point deltas
    int FDX12 = DX12 << 4;
    int FDX23 = DX23 << 4;
    int FDX31 = DX31 << 4;

    int FDY12 = DY12 << 4;
    int FDY23 = DY23 << 4;
    int FDY31 = DY31 << 4;

    // Bounding rectangle
    int minx = (min(X1, X2, X3) + 0xF) >> 4;
    int maxx = (max(X1, X2, X3) + 0xF) >> 4;
    int miny = (min(Y1, Y2, Y3) + 0xF) >> 4;
    int maxy = (max(Y1, Y2, Y3) + 0xF) >> 4;

    // Half-edge constants
    int C1 = DY12 * X1 - DX12 * Y1;
    int C2 = DY23 * X2 - DX23 * Y2;
    int C3 = DY31 * X3 - DX31 * Y3;

    // Correct for fill convention
    if (DY12 < 0 || (DY12 == 0 && DX12 > 0))
    {
      C1++;
    }
    if (DY23 < 0 || (DY23 == 0 && DX23 > 0))
    {
      C2++;
    }
    if (DY31 < 0 || (DY31 == 0 && DX31 > 0))
    {
      C3++;
    }

    int CY1 = C1 + DX12 * (miny << 4) - DY12 * (minx << 4);
    int CY2 = C2 + DX23 * (miny << 4) - DY23 * (minx << 4);
    int CY3 = C3 + DX31 * (miny << 4) - DY31 * (minx << 4);

    for (int y = miny; y < maxy; y++)
    {
      int CX1 = CY1;
      int CX2 = CY2;
      int CX3 = CY3;

      for (int x = minx; x < maxx; x++)
      {
        if (CX1 > 0 && CX2 > 0 && CX3 > 0)
        {
          backBuffer.setColor(Color.RED);
          backBuffer.drawRect(x, y, 0, 0);
        }

        CX1 -= FDY12;
        CX2 -= FDY23;
        CX3 -= FDY31;
      }

      CY1 += FDX12;
      CY2 += FDX23;
      CY3 += FDX31;
    }
  }

  void doubleStyle(Float2 v1, Float2 v2, Float2 v3)
  {
    // 28.4 fixed-point coordinates
    float y1 = iround(v1.y);
    float y2 = iround(v2.y);
    float y3 = iround(v3.y);

    float x1 = iround(v1.x);
    float x2 = iround(v2.x);
    float x3 = iround(v3.x);

    // Deltas
    float dx12 = (int) (x1 - x2);
    float dx23 = (int) (x2 - x3);
    float dx31 = (int) (x3 - x1);

    float dy12 = (int) (y1 - y2);
    float dy23 = (int) (y2 - y3);
    float dy31 = (int) (y3 - y1);

    // Bounding rectangle
    float minx = (int) ((min(x1, x2, x3)));
    float maxx = (int) ((max(x1, x2, x3)));
    float miny = (int) ((min(y1, y2, y3)));
    float maxy = (int) ((max(y1, y2, y3)));

    // Half-edge constants
    float c12 = dy12 * x1 - dx12 * y1;       //(Y1 - Y2) * X1 - (X1 - X2) * Y1
    float c23 = dy23 * x2 - dx23 * y2;
    float c31 = dy31 * x3 - dx31 * y3;

    // Correct for fill convention
    if (dy12 < 0 || (dy12 == 0 && dx12 > 0))
    {
      c12++;
    }
    if (dy23 < 0 || (dy23 == 0 && dx23 > 0))
    {
      c23++;
    }
    if (dy31 < 0 || (dy31 == 0 && dx31 > 0))
    {
      c31++;
    }

    float cy1 = c12 + dx12 * (miny) - dy12 * (minx); //((Y1 - Y2) * X1 - (X1 - X2) * Y1) + ((X1 - X2) * miny) - ((Y1 - Y2) * minx)
    float cy2 = c23 + dx23 * (miny) - dy23 * (minx);
    float cy3 = c31 + dx31 * (miny) - dy31 * (minx);

    float invDet12 = 1 / (c12 + dx12 * (y3) - dy12 * (x3));
    float invDet23 = 1 / (c23 + dx23 * (y1) - dy23 * (x1));
    float invDet31 = 1 / (c31 + dx31 * (y2) - dy31 * (x2));

    for (float y = miny; y < maxy; y++)
    {
      float cx1 = cy1;
      float cx2 = cy2;
      float cx3 = cy3;

      for (float x = minx; x < maxx; x++)
      {
        if (cx1 > 0 && cx2 > 0 && cx3 > 0)
        {
          float alpha = cx1 * invDet12;
          float beta = cx2 * invDet23;
          float gamma = cx3 * invDet31;

          backBuffer.setColor(new Color(alpha, beta, gamma));
          backBuffer.drawRect((int) x, (int) y, 0, 0);
        }

        cx1 -= dy12;
        cx2 -= dy23;
        cx3 -= dy31;
      }

      cy1 += dx12;
      cy2 += dx23;
      cy3 += dx31;
    }
  }

  void myStyle(Float2 v1, Float2 v2, Float2 v3)
  {
    roundInPlace(v1);
    roundInPlace(v2);
    roundInPlace(v3);

    float minx = (int) ((min(v1.x, v2.x, v3.x)));
    float maxx = (int) ((max(v1.x, v2.x, v3.x)));
    float miny = (int) ((min(v1.y, v2.y, v3.y)));
    float maxy = (int) ((max(v1.y, v2.y, v3.y)));

    float alphaDet = lineFunction(v3.x, v3.y, v1, v2);
    float betaDet = lineFunction(v1.x, v1.y, v2, v3);
    float gammaDet = lineFunction(v2.x, v2.y, v3, v1);

    for (float y = miny; y < maxy; y++)
    {
      for (float x = minx; x < maxx; x++)
      {
        float alpha = lineFunction(x, y, v1, v2);
        float beta = lineFunction(x, y, v2, v3);
        float gamma = lineFunction(x, y, v3, v1);

        float alphaRatio = alpha / alphaDet;
        float betaRatio = beta / betaDet;
        float gammaRatio = gamma / gammaDet;

        if ((alpha < 0) && (beta < 0) && (gamma < 0))
        {
          backBuffer.setColor(new Color(alphaRatio, betaRatio, gammaRatio));
          backBuffer.drawRect((int) x, (int) y, 0, 0);
        }
      }
    }
  }

  private int iround(float f)
  {
    return (int) (f + 0.5f);
  }

  private float lineFunction(float x, float y, Float2 p1, Float2 p2)
  {
    return (p2.x - p1.x) * (y - p1.y) - (p2.y - p1.y) * (x - p1.x);
  }

  private void roundInPlace(Float2 float2)
  {
    float2.x = (int) (float2.x + 0.5f);
    float2.y = (int) (float2.y + 0.5f);
  }
}

