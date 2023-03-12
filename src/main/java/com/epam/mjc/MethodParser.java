package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer tokenizer = new StringTokenizer(signatureString, ",()");
        List<String> modifiers = new ArrayList<>();
        modifiers.add("public");
        modifiers.add("private");
        modifiers.add("protected");

        List<String> pieces = new ArrayList<>();
        while (tokenizer.hasMoreTokens())
            pieces.add(tokenizer.nextToken());

        tokenizer = new StringTokenizer(pieces.get(0), " ");
        pieces.remove(0);

        List<String> modifierTypeName = new ArrayList<>();
        while (tokenizer.hasMoreTokens())
            modifierTypeName.add(tokenizer.nextToken());

        MethodSignature methodSignature = new MethodSignature(modifierTypeName.get(modifierTypeName.size()-1));
        for (int i = 0; i < modifiers.size(); i++) {
            if (modifierTypeName.get(0).equals(modifiers.get(i))) {
                methodSignature.setAccessModifier(modifiers.get(i));
                modifierTypeName.remove(0);
            }
        }
        modifiers.clear();
        methodSignature.setReturnType(modifierTypeName.get(0));
        modifierTypeName.clear();

        List<String> typeName = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i++) {
            StringTokenizer tokenizer1 = new StringTokenizer(pieces.get(i), " ");
            for (int j = 0; j <= tokenizer1.countTokens(); j++) {
                typeName.add(tokenizer1.nextToken());
            }
        }

        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        for (int i = 0; i < typeName.size(); i+=2) {
            MethodSignature.Argument argument = new MethodSignature.Argument(typeName.get(i), typeName.get(i+1));
            argumentList.add(argument);
        }
        methodSignature.setArguments(argumentList);

        return methodSignature;
    }
}
