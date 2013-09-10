package ${packageName}.proxy;

<#list importClassList as importClass>
import ${importClass};
</#list>

public interface I${shortClassName} {

	public static final String CLASSNAME = "${longClassName}";

<#list methodList as method>	
	public ${method.returnType} ${method.methodName}(${method.parameters});
</#list>
	
}