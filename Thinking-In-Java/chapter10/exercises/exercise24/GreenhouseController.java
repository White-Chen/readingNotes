package exercise24;

/**
 * \* Created with Chen Zhe on 1/5/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class GreenhouseController {
    public static void main(String[] args) {
        GreenController gc = new GreenController();
        gc.addEvent(gc.new Bell(1300));
        Event[] events = {
                gc.new ThermostatNight(0),
                gc.new LightOn(200),
                gc.new LigthOff(400),
                gc.new WaterOn(600),
                gc.new WaterOff(800),
                gc.new FansOn(1000),
                gc.new FansOff(1200),
                gc.new ThermostatDay(1400)
        };
        gc.addEvent(gc.new Restart(2000,events));
        if(args.length == 1)
            gc.addEvent(
                    new GreenController.Terminate(
                            new Integer(args[0])
                    )
            );
        gc.run();
    }
}
