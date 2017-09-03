/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stelinno.microservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockPriceController {
  @RequestMapping("/home")
  public String home() {
    return "Welcome to the Stock Price service";
  }
  
  @RequestMapping("/stocks")
  public @ResponseBody List<Stock> stocks() {
    List<Stock> stocks = new ArrayList<>();
    Stock stock = new Stock();
    stock.setExchange("NYSE");
    stock.setName("American Micro Devices");
    stock.setSymbol("NASDAQ:AMD");
    stock.setQuotePrice(13.95);
    stock.setQuoteTime(new Date());        
    stocks.add(stock);
    
    stock = new Stock();
    stock.setExchange("NYSE");
    stock.setName("Intel");
    stock.setSymbol("NASDAQ:INTC");
    stock.setQuotePrice(34.45);
    stock.setQuoteTime(new Date());        
    stocks.add(stock);    
    
    return stocks;
  }

  /**
   * <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
   * App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
   */
  @RequestMapping("/_ah/health")
  public String healthy() {
    // Message body required though ignored
    return "Still surviving.";
  }
  
  @RequestMapping("/version")
  public String version() {
    return "V1.0.14102017_1550";
  }

}
