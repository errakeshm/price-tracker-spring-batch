# A Spring Batch POC for a price tracker

## Overview
- The application tracks the prices of products.
- Schedule is set based on a cron expression
- Concepts utilized : Spring batch, Multi threading, Spring scheduler, Design patterns (Visitor, Fluent)


## Functionalities (Under Development)
- You can add a list of urls in beans.xml. 
- You can add an url using a rest service
- Right now the data is printed straight to the console.

``` 

URL : localhost:8080/product
Body : 
{
	"name":"Samsung Galaxy",
  	"category":"amazon",
  	"url":"https://www.amazon.in/Samsung-Galaxy-Electric-Blue-128GB-Storage/dp/B085J1J32G/ref=gbph_img_m-2_b503_45f6d7c3?smid=A1EWEIV3F4B24B&pf_rd_p=55f6cf50-fbbe-4bc8-b88d-d0dac8cbb503&pf_rd_s=merchandised-search-2&pf_rd_t=101&pf_rd_i=1389401031&pf_rd_m=A1VBAL9TL5WCBF&pf_rd_r=1YX5MC6AC2MG6VD2HBAR"
}

```
 

