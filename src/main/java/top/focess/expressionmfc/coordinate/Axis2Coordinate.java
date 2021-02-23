package top.focess.expressionmfc.coordinate;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.EquationImp;
import top.focess.expressionmfc.equation.Solution;
import top.focess.expressionmfc.equation.range.Range;
import top.focess.expressionmfc.exception.*;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.operator.Operator;
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

    public @NonNull Axis2CoordinateFunction append(EquationImp equation) {
        return this.append(equation,Argument.getArgument("x"),Argument.getArgument("y"));
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
            int base = Axis2Coordinate.this.getFunctions().size() * width * width * 100;
            int pos = 0;
            for (CoordinateFunction c : Axis2Coordinate.this.getFunctions()) {
                Axis2CoordinateFunction function = (Axis2CoordinateFunction) c;
                g.setColor(function.getColor());
                Simplifiable simplifiable = Operator.MINUS.operate(function.getEquation().getLeft(),function.getEquation().getRight()).simplify();
                for (double x = -width /200.0 ; x < width /200.0; x+=0.001)
                for (double y = -height / 200.0 ; y < height / 200.0; y+= 0.001){
                    pos++;
                    System.out.println(100*pos/(double)base);
                    function.getX().setValue(new SimpleConstantDouble(x));
                    function.getY().setValue(new SimpleConstantDouble(y));
                    try {
                        if (MathHelper.abs(simplifiable.value().doubleValue()) < 0.001)
                            g.fillOval((int) (x*100 + width / 2), (int) (height / 2 - y*100), 2, 2);//todo
                    } catch (UnknownArgumentException | DividedByZeroException e) {
                        throw new CoordinateShowException(e);
                    }
                }
            }
        }
    }
}
