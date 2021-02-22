package top.focess.expressionmfc.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.EquationImp;
import top.focess.expressionmfc.exception.CoordinateShowException;
import top.focess.expressionmfc.exception.DividedByZeroException;
import top.focess.expressionmfc.exception.IllegalUnknownArgumentException;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.util.MathHelper;

import javax.swing.*;
import java.awt.*;

public class Axis2Coordinate extends Coordinate {


    private final CoordinateFrame frame;

    public Axis2Coordinate() {
        this.frame = new CoordinateFrame();
    }

    @NonNull
    public Axis2CoordinateFunction append(EquationImp equation, Argument x, Argument y, Color color) {
        return (Axis2CoordinateFunction) this.add(new Axis2CoordinateFunction(equation, x, y, color));
    }

    @NonNull
    public Axis2CoordinateFunction append(EquationImp equation, Argument x, Argument y) {
        return this.append(equation, x, y, Color.BLUE);
    }

    @Override
    public void show() {
        if (this.frame.isVisible())
            this.frame.repaint();
        else this.frame.setVisible(true);
    }

    public static class Axis2CoordinateFunction extends CoordinateFunction {

        Axis2CoordinateFunction(EquationImp equation, Argument x, Argument y, Color color) {
            super(equation, color, x, y);
        }

        @NonNull
        public Argument getX() {
            return this.getArgument(0);
        }

        @NonNull
        public Argument getY() {
            return this.getArgument(1);
        }
    }

    private class CoordinateFrame extends JFrame {

        public CoordinateFrame() {
            this(500, 500);
        }

        public CoordinateFrame(int width, int height) {
            this("2-Axis-Coordinate", width, height);
        }

        public CoordinateFrame(String title, int width, int height) {
            super(title);
            this.setSize(width, height);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.add(new CoordinatePanel());
        }

    }

    private class CoordinatePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.black);
            int height = this.getHeight();
            int width = this.getWidth();
            g.drawLine(0, height / 2, width, height / 2);
            g.drawLine(width / 2, 0, width / 2, height);

            for (CoordinateFunction c : Axis2Coordinate.this.getFunctions()) {
                Axis2CoordinateFunction function = (Axis2CoordinateFunction) c;
                g.setColor(function.getColor());
                for (int i = -width / 2; i < width / 2; i++) {
                    double x = i;//todo
                    double y;
                    function.getX().setValue(new SimpleConstantDouble(x));
                    try {
                        y = function.getEquation().solve(function.getY()).getAnswer().doubleValue();
                    } catch (DividedByZeroException ignored) {
                        continue;
                    } catch (UnknownArgumentNotFoundException | IllegalUnknownArgumentException e) {
                        throw new CoordinateShowException(e);
                    }
                    if (MathHelper.abs(y) < height / 2.0)
                        g.fillOval((int) (x + width / 2), (int) (y + height / 2), 2, 2);//todo
                }
            }
        }
    }
}
