package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model;


public class AnnualNicsSummary {

    private final Class1 class1;
    private final Class2 class2;
    private final boolean maxNICsReached;

    public AnnualNicsSummary(
            final Class1 class1,
            final Class2 class2,
            final boolean maxNICsReached) {
        this.class1 = class1;
        this.class2 = class2;
        this.maxNICsReached = maxNICsReached;
    }

    public Class1 getClass1() {
        return class1;
    }

    public Class2 getClass2() {
        return class2;
    }

    public boolean isMaxNICsReached() {
        return maxNICsReached;
    }

}