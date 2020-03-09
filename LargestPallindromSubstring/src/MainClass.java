	import java.util.SortedMap;
	import java.util.TreeMap;
	
	
	public class MainClass {
		public static void main(String[] args) {
			findLargestPallindrom("aadwfaafw");
			findLargestPallindrom("billkrbatcctabrasdfasfdabcba");
			findLargestPallindrom("assdfdssa");
			findLargestPallindrom("asffsabc");
			findLargestPallindrom("bb");
		}
	
		private static void findLargestPallindrom(String string) {
			SortedMap<Integer, String> pallindroms = new TreeMap<>();
			
			int largestLength=0;
			for(int i =0; i< string.length()-1; i++)
			{
				if(string.charAt(i)==string.charAt(i+1))
				{
					largestLength =2;
					pallindroms.put(2, string.substring(i, i+2));
					//System.out.println(string.substring(i, i+2));
				}
			}
			
			String sub;
			//check different length of substrings for being pallindrom:
			for(int i=3; i<=string.length(); i++) {
				for(int k=0; k+i < string.length()+1; k++) {
					if(k+i+1 >= string.length()) {
						sub = string.substring(k);
					}
					else {
						sub = string.substring(k, k+i+1);
					}
					
					if(MainClass.isPallindrom(sub)) {
						//System.out.println("added large substring is: "+sub);
						pallindroms.put(i, sub);
					}
				}
			}
			
			if(!pallindroms.isEmpty()) {
				System.out.println("largest palindrome substring is:" + pallindroms.get(pallindroms.lastKey()));
			}
			else System.out.println("There is no pallindrom");
		}
		
		static boolean isPallindrom(String s) {
			StringBuilder sb = new StringBuilder(s);
			if(s.equals(sb.reverse().toString())) {
				return true;
			}
			return false;
		}
	}
