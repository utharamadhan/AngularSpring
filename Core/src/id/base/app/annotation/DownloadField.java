package id.base.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DownloadField {
	public abstract boolean isForDownload() default false;
	public abstract String headerName();
	public abstract int position();
}
