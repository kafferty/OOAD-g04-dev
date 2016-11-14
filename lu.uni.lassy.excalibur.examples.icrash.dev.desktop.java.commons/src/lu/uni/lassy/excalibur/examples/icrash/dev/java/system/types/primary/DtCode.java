package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

public class DtCode implements JIntIs, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<Integer, DtReal> functionResults = new HashMap<>();
	private DtString generatedCode;
	private int _Length = 6;
	
	public DtCode(DtDateAndTime clock) {
		Long x = Long.valueOf(String.valueOf((
				(clock.date.year.value.getValue() * 365 * 30 * 24 * 60 * 60 +
				clock.date.month.value.getValue() * 30 * 24 * 60 * 60 +
				clock.date.day.value.getValue() * 24 * 60 * 60 + 
				clock.time.hour.value.getValue() * 60 * 60 + 
				clock.time.minute.value.getValue() * 60  + 
				clock.time.second.value.getValue())) * 1000 
				));
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("current time = " + x);
		Random rnd = new Random(x);
		log.info("random has created.. ");
		functionResults.put(1, new DtReal(new PtReal(Math.cos(Double.valueOf(x)))));
		functionResults.put(2, new DtReal(new PtReal(Math.sin(Double.valueOf(x)))));
		functionResults.put(3, new DtReal(new PtReal(Math.tan(Double.valueOf(x)))));
		functionResults.put(4, new DtReal(new PtReal(1 / Math.tan(Double.valueOf(x)))));
		log.info("functionResults were filled.. " + functionResults.get(1 + rnd.nextInt(4)).value.getValue());
		DtReal code = new DtReal(new PtReal(functionResults.get(1 + rnd.nextInt(4)).value.getValue() + 
				functionResults.get(1 + rnd.nextInt(4)).value.getValue() - 
				functionResults.get(1 + rnd.nextInt(4)).value.getValue() +
				functionResults.get(1 + rnd.nextInt(4)).value.getValue() -
				functionResults.get(1 + rnd.nextInt(4)).value.getValue()));
		log.info("real code: " + code.value.getValue());
		generatedCode = new DtString(new PtString(String.valueOf(code.value.getValue()).substring(String.valueOf(code.value.getValue()).length()-6)));
		log.info("generatedCode = " + generatedCode.value.getValue());
	}
	
	public DtString getCode() {
		return generatedCode;
	}
	
	@Override
	public PtBoolean is() {
		return new PtBoolean(this.getCode().value.getValue().length() == _Length);
	}


}
