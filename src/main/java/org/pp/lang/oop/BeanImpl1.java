package org.pp.lang.oop;

/**
 * 参考 https://blog.csdn.net/A_Runner/article/details/102494963
 */
@Autowired(name = BeanImpl2.class/*如何动态添加属性？*/)
public class BeanImpl1 {
    private static BeanImpl2 beanImpl2;

    static {
        beanImpl2 = new BeanImpl2();
    }

    private BeanImpl2 beanImpl;

    {
        beanImpl2.hashCode();
    }

    public BeanImpl1(BeanImpl2 beanImpl) {

    }

    public BeanImpl2 function() {
        return null;
    }

    public void function(BeanImpl2 beanImpl) {
    }
}
