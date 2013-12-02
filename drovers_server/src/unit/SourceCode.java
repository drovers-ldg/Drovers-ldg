package unit;

public class SourceCode{
	public int id;
	public CodeBlock[] code;
	
	public SourceCode(CodeBlock [] code){	
		this.code = code;
	}
	
	class CodeBlock{
		public int type;
		public int dataType;
		
		CodeBlock(int type, int dataType){
			this.type = type;
			this.dataType = dataType;
		}
	}
}