package net.mindview.test.chapter_1_12;

/**
 * \* Created with Chen Zhe on 12/25/2016.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Factories_1 {

    /**
     * Service consumer.
     * 产品的消费者
     * @param factory the factory
     */
    public static void serviceConsumer(ServiceFactory factory){
        Service service = factory.getService();
        service.method1();
        service.method2();
    }

    /**
     * The entry point of application.
     *  测试类
     * @param args the input arguments
     */
    public static void main(String[] args) {
        serviceConsumer(new ServiceFactoryImpl_1());
        serviceConsumer(new ServiceFactoryImpl_2());
    }
}

/**
 * The interface Service.
 * 抽象产品接口
 */
interface Service{
    /**
     * Method 1.
     */
    void method1();

    /**
     * Method 2.
     */
    void method2();
}

/**
 * The interface Service factory.
 * 抽象工厂接口
 */
interface ServiceFactory{
    /**
     * Gets service.
     *
     * @return the service
     */
    Service getService();
}

/**
 * The type Service impl 1.
 * 具体产品角色
 */
class ServiceImpl_1 implements Service{

    public void method1() {
        System.out.println("ServiceImpl_1 method1()");
    }

    public void method2() {
        System.out.println("ServiceImpl_1 method2()");
    }
}

/**
 * The type Service impl 2.
 * 具体产品角色
 */
class ServiceImpl_2 implements Service{

    public void method1() {
        System.out.println("ServiceImpl_2 method1()");
    }

    public void method2() {
        System.out.println("ServiceImpl_2 method2()");
    }
}

/**
 * The type Service factory impl 1.
 * 具体工厂角色1
 */
class ServiceFactoryImpl_1 implements ServiceFactory{
    public Service getService() {
        return new ServiceImpl_1();
    }
}

/**
 * The type Service factory impl 2.
 * 具体工厂角色2
 */
class ServiceFactoryImpl_2 implements ServiceFactory{

    public Service getService() {
        return new ServiceImpl_2();
    }
}