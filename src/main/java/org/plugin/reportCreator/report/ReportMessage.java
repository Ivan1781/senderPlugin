package org.plugin.reportCreator.report;

import java.util.Objects;

public class ReportMessage implements Report {
    private String testClassName;
    private String testName;
    private String result;

    private ReportMessage(ReportMessageBuilder builder) {
       this.testClassName = builder.testClassName;
       this.testName = builder.testName;
       this.result = builder.result;
    }

    public static class ReportMessageBuilder {
   
        private String testClassName;
       
        private String testName;
     
        private String result;

        public ReportMessageBuilder() {
        }
        public ReportMessageBuilder setTestClassName(String testClassName) {
            this.testClassName = testClassName;
            return this;
        }

        public ReportMessageBuilder setTestName(String testName) {
            this.testName = testName;
            return this;
        }

        public ReportMessageBuilder setResult(String result) {
            this.result = result;
            return this;
        }

        public ReportMessage build() {
            return new ReportMessage(this);
        }
    }

    @Override
    public String toString() {
        return "ReportMessage{" +
                "testClassName='" + testClassName + '\'' +
                ", testName='" + testName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportMessage that = (ReportMessage) o;
        return Objects.equals(testClassName, that.testClassName) && Objects.equals(testName, that.testName) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testClassName, testName, result);
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
