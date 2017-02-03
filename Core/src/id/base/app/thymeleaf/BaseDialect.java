package id.base.app.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class BaseDialect extends AbstractDialect {
	
	public BaseDialect() {
		super();
	}

	@Override
	public String getPrefix() {
		return "ifs";
	}

	@Override
	  public Set<IProcessor> getProcessors() {
	    final Set<IProcessor> processors = new HashSet<IProcessor>();
	    processors.add(new RoleCheckerProcessor());
	    processors.add(new RoleInCheckerProcessor());
	    processors.add(new RoleNotInCheckerProcessor());
	    return processors;
	  }
}
