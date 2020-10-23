package ru.ssau.tk.kintoho.LR.functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {
    private Node head;
    private int count;

    private void addNode(double x, double y) {
        Node joint = new Node();
        if (head == null) {
            joint.x = x;
            joint.y = y;
            joint = head;
            joint.prev = joint;
            joint.next = joint;
        } else {
            joint.next = head;
            joint.prev = head.prev;
            joint.x = x;
            joint.y = y;
            head.prev.next = joint;
            head.prev = joint;
        }
        count++;
    }

    LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        this.count = xValues.length;
        for (int iterator = 0; iterator < count; iterator++) {
            this.addNode(xValues[iterator], yValues[iterator]);
        }
    }

    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        this.count = count;
        double step = (xTo - xFrom) / (count - 1);
        double xMomentValue = xFrom;
        for (int iterator = 0; iterator < count; iterator++) {
            this.addNode(xMomentValue, source.apply(xMomentValue));
            xMomentValue += step;
        }
    }

    private Node getNode(int index) {
        Node currentNode = head;

        for (int iterator = 0; iterator < index; iterator++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        int iterator = 0;
        while (iterator < count) {
            if (getNode(iterator).x == x) {
                return iterator;
            }
            iterator++;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        int iterator = 0;
        while (iterator < count) {
            if (getNode(iterator).y == y) {
                return iterator;
            }
            iterator++;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        if (x < head.x) {
            return 0;
        }
        for (int iterator = 0; iterator + 1 < count; iterator++) {
            if (getNode(iterator).next.x > x) {
                return iterator;
            }
        }
        return count;
    }
}


