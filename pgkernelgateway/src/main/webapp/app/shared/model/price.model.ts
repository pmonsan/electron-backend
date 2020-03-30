import { Moment } from 'moment';

export interface IPrice {
  id?: number;
  code?: string;
  label?: string;
  amount?: number;
  percent?: number;
  amountInPercent?: boolean;
  amountTransactionMax?: number;
  amountTransactionMin?: number;
  currencyCode?: string;
  serviceCode?: string;
  description?: string;
  modificationDate?: Moment;
  active?: boolean;
  pricePlanId?: number;
}

export class Price implements IPrice {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public amount?: number,
    public percent?: number,
    public amountInPercent?: boolean,
    public amountTransactionMax?: number,
    public amountTransactionMin?: number,
    public currencyCode?: string,
    public serviceCode?: string,
    public description?: string,
    public modificationDate?: Moment,
    public active?: boolean,
    public pricePlanId?: number
  ) {
    this.amountInPercent = this.amountInPercent || false;
    this.active = this.active || false;
  }
}
