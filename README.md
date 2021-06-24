# TradeStory

Technologies used:
1) Spring Boot
2) In memory H2 database
3) Spring data JPA repository
4) Junit
5) Quartz for cron expressions java

{
"tradeId" : "T1",
"tradeVersion" : 1,
"counterPartyId" :  "CP-1",
"bookId": "B1", 
"maturityDate": "2021-06-22",
"expired" : "N"
}

There are couples of validation, we need to provide in the above assignment
During transmission if the lower version is being received by the store it will reject the trade and throw an exception. If the version is same it will override the existing record.
Store should not allow the trade which has less maturity date then today date.
Store should automatically update the expire flag if in a store the trade crosses the maturity date.

I have written quartz schedular which we can control from the rest calls. Schedule/Reschedule/Delete job.


GET /tradeStory/getAllTradeStories  
POST /tradeStory/insertTradeStory     -  Taking care of all validations and insert/update. 
/tradeStory/markExpiredTrade?date=dd/MM/yyyy    POST rest call to mark trade before given date as expired
GET rest call to schedule job runs daily as per cron and marked expired to trades whose maturity dates have been passed.
/tradeStory/scheduleJob?taskName=MARK_TRADE_EXPIRED&cronExpression=0/30 * * * * ? *    
/tradeStory/rescheduleJob?taskName=MARK_TRADE_EXPIREDcronExpression=0/30 * * * * ? *
/tradeStory/deleteJob?taskName=MARK_TRADE_EXPIRED
