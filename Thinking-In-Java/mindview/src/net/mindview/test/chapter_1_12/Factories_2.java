package net.mindview.test.chapter_1_12;

/**
 * \* Created with Chen Zhe on 1/3/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Factories_2 {

    public static void serviceConsumer(ServiceFactory factory){
        Service service = factory.getService();
        service.method1();
        service.method2();
    }

    public static void main(String[] args) {
        serviceConsumer(ServiceImplementation_1.factory);
        serviceConsumer(ServiceImplementation_2.factory);
    }
}

/**
 * The type Service impl 2.
 * 具体产品角色
 */
class ServiceImplementation_1 implements Service{

    private ServiceImplementation_1(){}

    @Override
    public void method1() {
        System.out.println("ServiceImplementaion_1 method1");
    }

    @Override
    public void method2() {
        System.out.println("ServiceImplementaion_2 method1");
    }

    /**
     * The type Service factory impl 1.
     * 具体工厂角色1
     */
    public static ServiceFactory factory =
            new ServiceFactory() {
                @Override
                public Service getService() {
                    return new ServiceImplementation_1();
                }
            };
}

/**
 * The type Service impl 2.
 * 具体产品角色
 */
class ServiceImplementation_2 implements Service{

    private ServiceImplementation_2(){}

    @Override
    public void method1() {
        System.out.println("ServiceImplementaion_2 method1");
    }

    @Override
    public void method2() {
        System.out.println("ServiceImplementaion_2 method2");
    }

    /**
     * The type Service factory impl 1.
     * 具体工厂角色1
     */
    public static ServiceFactory factory =
            new ServiceFactory() {
                @Override
                public Service getService() {
                    return new ServiceImplementation_2();
                }
            };
}