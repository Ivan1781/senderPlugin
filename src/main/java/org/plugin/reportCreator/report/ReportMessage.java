package org.plugin.reportCreator.report;

public class ReportMessage {
    private String testClassName;
    private String testName;
    private String result;

    private String failure;

    private ReportMessage(ReportMessageBuilder builder) {
       this.testClassName = builder.testClassName;
       this.testName = builder.testName;
       this.result = builder.result;
       this.failure = builder.failure;
    }


    public static class ReportMessageBuilder {
   
        private String testClassName;
       
        private String testName;
     
        private String result;
        private String failure;

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

        public ReportMessageBuilder setFailure(String failure) {
            this.failure = failure;
            return this;
        }

        public ReportMessage build() {
            return new ReportMessage(this);
        }
    }


    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
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
