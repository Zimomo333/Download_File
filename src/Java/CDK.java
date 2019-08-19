public class CDK {
    private String Number;
    private Integer Times;
    private Integer Used;

    public CDK(String Number,Integer Times,Integer Used){
        this.Number=Number;
        this.Times=Times;
        this.Used=Used;
    }

    public String getNumber(){
        return Number;
    }
    public void setNumber(String Number){
        this.Number=Number;
    }

    public Integer getTimes(){
        return Times;
    }
    public void setTimes(Integer Times){
        this.Times=Times;
    }

    public Integer getUsed(){
        return Used;
    }
    public void setUsed(Integer Used){
        this.Used=Used;
    }

    public CDK() {
        super();
        // TODO Auto-generated constructor stub
    }
}
