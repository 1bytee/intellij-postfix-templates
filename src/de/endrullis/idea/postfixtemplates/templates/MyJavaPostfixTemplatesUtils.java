package de.endrullis.idea.postfixtemplates.templates;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Some static additions to {@link com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils}.
 *
 * @author Stefan Endrullis &lt;stefan@endrullis.de&gt;
 */
public abstract class MyJavaPostfixTemplatesUtils {

	private MyJavaPostfixTemplatesUtils() {
	}

	public static Condition<PsiElement> isCustomClass(String clazz) {
		return element -> element instanceof PsiExpression && isCustomClass(((PsiExpression) element).getType(), clazz);
	}

	public static final Condition<PsiElement> IS_DECIMAL_NUMBER =
		element -> element instanceof PsiExpression && isDecimalNumber(((PsiExpression) element).getType());

	/**
	 * Contains byte, char, int, long, float, and double.
	 */
	public static final Set<PsiType> NUMERIC_TYPES = new HashSet<>(Arrays.asList(
		PsiType.BYTE, PsiType.CHAR, PsiType.INT, PsiType.LONG, PsiType.FLOAT, PsiType.DOUBLE)
	);

	@Contract("null,_ -> false")
	public static boolean isCustomClass(@Nullable PsiType type, @NotNull String clazz) {
		return type != null && InheritanceUtil.isInheritor(type, clazz);
	}

	@Contract("null -> false")
	public static boolean isDecimalNumber(@Nullable PsiType type) {
		if (type == null) {
			return false;
		}

		return NUMERIC_TYPES.contains(type) || NUMERIC_TYPES.contains(PsiPrimitiveType.getUnboxedType(type));
	}

}
