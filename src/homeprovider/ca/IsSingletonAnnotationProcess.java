package homeprovider.ca;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;


@SupportedAnnotationTypes(value = {"homeprovider.ca.IsSingleton"})
public class IsSingletonAnnotationProcess  extends AbstractProcessor{
	
	 
	private Messager messenger;
	
	@Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        
        messenger = processingEnv.getMessager();
    }
	
	 @Override
	    public boolean process(Set<? extends TypeElement> annotations,
	            RoundEnvironment roundEnv) {

	        TypeElement IsSingleton = processingEnv.getElementUtils().getTypeElement("homeprovider.ca.IsSingleton");
	        Set<? extends Element> classes = roundEnv.getElementsAnnotatedWith(IsSingleton);
	         messenger = processingEnv.getMessager();
	        for (Element e : classes) {
	            boolean found = false;
	            for (Element method : e.getEnclosedElements()) {
	                messenger.printMessage(Kind.ERROR, 
	                        method.getSimpleName());
	                if (method.getKind() == ElementKind.METHOD && method.getSimpleName().toString().equals("getInstance")) {
	                    found = true;
	                    break;
	                }
	            }
	            if (!found)
	            {
	                messenger.printMessage(Kind.ERROR, 
	                    "The following class does not implement getInstance : " + e.getSimpleName(),e);
	            }
	        }
	        return true;
	    }

}
