package offer;

/**
 * Created by wiyee on 2018/3/10.
 */
public class JumpFloor {
    public static void main(String[] args) {
        JumpFloor jumpFloor = new JumpFloor();
        System.out.println(jumpFloor.JumpFloor(4));
        System.out.println(jumpFloor.JumpFloorII(4));
    }

    public int JumpFloor(int target) {
        if (target <= 0)
            return 0;
        else if (target == 1)
            return 1;
        else if (target == 2)
            return 2;
        else {
            return JumpFloor(target - 2) + JumpFloor(target - 1);
        }
    }

    public int JumpFloorII(int target) {
        if (target <= 0)
            return 0;
        else if (target == 1)
            return 1;
        else if (target == 2)
            return 2;
        else {
            return 2 * JumpFloorII(target - 1);
        }

    }
}
