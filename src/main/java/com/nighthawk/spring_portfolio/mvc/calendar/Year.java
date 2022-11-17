package com.nighthawk.spring_portfolio.mvc.calendar;

/** Simple POJO 
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
   private boolean isLeapYear;
   private int firstDayOfYear;

   // zero argument constructor
   public Year() {} 

   /* year getter/setters */
   public int getYear() {
      return year;
   }
   public void setYear(int year) {
      this.year = year;
      this.setIsLeapYear(year);
      this.setFirstDayOfYear(year);
   }

   /* isLeapYear getter/setters */
   public boolean getIsLeapYear(int year) {
      return APCalendar.isLeapYear(year);
   }
   private void setIsLeapYear(int year) {  // this is private to avoid tampering
      this.isLeapYear = APCalendar.isLeapYear(year);
   }

   /* isLeapYearToString formatted to be mapped to JSON */
   public String isLeapYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"isLeapYear\": "  +this.isLeapYear+ " }" );
   }	

   // FirstDayOfYear getters/setters
   public int getFirstDayOfYear(int year) {
        return APCalendar.firstDayOfYear(year);
   }
   private void setFirstDayOfYear(int year) {
        this.firstDayOfYear = APCalendar.firstDayOfYear(year);
   }
   public String firstDayOfYearToString (int year) {
    return ( "{ \"year\": "  +this.year+  ", " + "\"firstDayOfYear\": "  +this.firstDayOfYear+ " }" );
   }

   public String dayOfYearToString(int month, int day, int year) {
    return ( "{ \"month\": "  + month +  ", " + "\"day\": "  + day + ", " +  "\"year\": "  +this.year+  ", " + "\"dayOfYear\": "  + APCalendar.dayOfYear(month, day, year)+ " }" );
   }

   /* standard toString placeholder until class is extended */
   public String toString() { 
      return ( "{ \"year\": "  +this.year+  ", " + "\"firstDayOfYear\": "  +this.firstDayOfYear+   ", " + "\"firstDayOfYear\": "  +this.firstDayOfYear+ " }" ); 
   }

   public static void main(String[] args) {
      Year year = new Year();
      year.setYear(2022);
      System.out.println(year);
   }
}