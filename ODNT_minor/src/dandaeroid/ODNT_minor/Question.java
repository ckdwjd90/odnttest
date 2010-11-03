package dandaeroid.ODNT_minor;

public class Question {
	int _id;
	String _keyword;
	String _cate01;
	String _cate02;
	int _answer;
	String _solution;
	int _rate;
	String _filename;

	public Question(int _id, String _keyword, String _cate01, String _cate02, int _answer,
			String _solution, int _rate, String _filename) {
		this._id = _id;
		this._keyword = _keyword;
		this._cate01 = _cate01;
		this._cate02 = _cate02;
		this._answer = _answer;
		this._solution = _solution;
		this._rate = _rate;
		this._filename = _filename;
	}
}
