## Result of running on tomee/container/openejb-core
(There are also some "cannot find symbol" errors, I ommit them because it seems caused by some classpath config problem for unknown reasons)

- Total error files: 15 
- total errors reported: 12
- total warnings reported: 8
- confirmed true positives: 3
- confirmed false positives: 2
- unkown positives (un-related to Read Checker): 15



===== Read Check related error ===== 2 files ===== 2 false positives ===== 3 ture positives =====

/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SuperProperties.java:527: error: [cast.unsafe] "@UnsafeRead int" may not be casted to the type "@UnsafeRead char"
                            nextChar = (char) nextByte; // & 0xff
                                       ^
/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SuperProperties.java:988: error: [cast.unsafe] "@UnsafeRead int" may not be casted to the type "@UnsafeRead char"
                                nextChar = (char) nextByte; // & 0xff
                                           ^
2 errors


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java:50: error: [cast.unsafe] "@UnsafeRead int" may not be casted to the type "@UnsafeRead char"
                    current = (char) read;
                              ^
/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java:67: error: [cast.unsafe] "@UnsafeRead int" may not be casted to the type "@UnsafeRead char"
                    current = (char) read;
                              ^
/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java:88: error: [cast.unsafe] "@UnsafeRead int" may not be casted to the type "@UnsafeRead char"
            c = (char) read;
                ^
3 errors



===== generic type error ===== 5 files ===== 6 unknown positives =====

/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/meta/MetaTest.java:111: error: [argument.type.incompatible] incompatible types in argument.
                    final EnterpriseBean bean = expected.addEnterpriseBean(newBean(beanType, annotation.expected()));
                                                                                  ^
  found   : ? extends @UnknownSafety EnterpriseBean
  required: ? extends @UnknownSafety EnterpriseBean
/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/meta/MetaTest.java:114: error: [argument.type.incompatible] incompatible types in argument.
                    actual.addEnterpriseBean(newBean(beanType, annotation.actual()));
                                                    ^
  found   : ? extends @UnknownSafety EnterpriseBean
  required: ? extends @UnknownSafety EnterpriseBean
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/meta/MetaTest.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
2 errors


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/core/stateless/StatelessInstanceManager.java:221: error: [assignment.type.incompatible] incompatible types in assignment.
                instance = entry.get();
                                    ^
  found   : T extends @UnknownSafety Object
  required: @UnknownSafety Instance
1 error


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/assembler/classic/JndiEncBuilder.java:273: error: [type.argument.type.incompatible] incompatible types in type argument.
                    obj = Enum.valueOf(type, entry.value.trim());
                                      ^
  found   : @UnknownSafety Enum<T extends @UnknownSafety Enum<T>>
  required: @UnknownSafety Enum<@UnknownSafety Enum<T extends @UnknownSafety Enum<T>>>
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/assembler/classic/JndiEncBuilder.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
1 error


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/Pool.java:942: error: [override.return.invalid] @UnknownSafety Object create(@UnknownSafety NoSupplier this) in org.apache.openejb.util.Pool.NoSupplier cannot override T extends @UnknownSafety Object create(@UnknownSafety Supplier<T extends @UnknownSafety Object> this) in org.apache.openejb.util.Pool.Supplier; attempting to use an incompatible return type
        public Object create() {
               ^
  found   : @UnknownSafety Object
  required: T extends @UnknownSafety Object
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/Pool.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
1 error


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/threads/future/CUFuture.java:64: error: [override.param.invalid] @UnknownSafety int compareTo(@UnknownSafety CUFuture<V extends @UnknownSafety Object, C extends @UnknownSafety Object> this, C extends @UnknownSafety Object p0) in org.apache.openejb.threads.future.CUFuture cannot override @UnknownSafety int compareTo(@UnknownSafety Comparable<C extends @UnknownSafety Object> this, C extends @PolyAll Object p0) in java.lang.Comparable; attempting to use an incompatible parameter type
    public int compareTo(final C o) {
                                 ^
  found   : C extends @UnknownSafety Object
  required: C extends @PolyAll Object
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/threads/future/CUFuture.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
1 error



===== cast to xxx for a varargs call ===== 7 files ===== 8 unknown warnings =====

/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/config/AutoConfigPersistenceUnitsTest.java:262: warning: non-varargs call of varargs method with inexact argument type for last parameter;
        final String expected = Join.join("\n", supplied.classpath);
                                                        ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/config/AutoConfigPersistenceUnitsTest.java:263: warning: non-varargs call of varargs method with inexact argument type for last parameter;
        final String actual = Join.join("\n", generated.classpath);
                                                       ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/test/java/org/apache/openejb/config/AutoConfigPersistenceUnitsTest.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
2 warnings


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/config/RemoteServer.java:350: warning: non-varargs call of varargs method with inexact argument type for last parameter;
                    System.out.println(Join.join("\n", args));
                                                       ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning
1 warning

/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/config/typed/util/ProviderGenerator.java:332: warning: non-varargs call of varargs method with inexact argument type for last parameter;
        return Join.join(", ", split);
                               ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning
1 warning


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/config/NewLoaderLogic.java:418: warning: non-varargs call of varargs method with inexact argument type for last parameter;
                packages.add(Join.join(".", parts));
                                            ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/config/NewLoaderLogic.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
1 warning


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/testing/ApplicationComposers.java:325: warning: non-varargs call of varargs method with inexact argument type for last parameter;
                    final String gripe = "@Module method must return " + Join.join(" or ", MODULE_TYPES).replaceAll("(class|interface) ", "");
                                                                                           ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/testing/ApplicationComposers.java:933: warning: non-varargs call of varargs method with inexact argument type for last parameter;
            pojoDeployment.getProperties().setProperty("cxf.jaxrs.providers", Join.join(",", providersClasses).replace("class ", ""));
                                                                                             ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning


/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/testing/ApplicationComposers.java:1319: warning: non-varargs call of varargs method with inexact argument type for last parameter;
            configuration.setProperty("openejb.embedded.http.resources", Join.join(",", webResource.value()));
                                                                                                         ^
  cast to Object for a varargs call
  cast to Object[] for a non-varargs call and to suppress this warning
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/testing/ApplicationComposers.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/testing/ApplicationComposers.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
3 warnings



=====Others type of error===== 1 file ===== 1 unkonwn positives =====

/Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/Index.java:168: error: interface ListSet inherits unrelated defaults for spliterator() from types List and Set
    public interface ListSet extends List, Set {
           ^
Note: /Users/charleszhuochen/Programming/UWaterloo/jsr308/ReadChecker/Analysis/tomee/container/openejb-core/src/main/java/org/apache/openejb/util/Index.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
1 error
