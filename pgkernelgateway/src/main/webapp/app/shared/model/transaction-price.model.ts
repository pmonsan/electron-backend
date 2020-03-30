import { Moment } from 'moment';

export interface ITransactionPrice {
  id?: number;
  code?: string;
  label?: string;
  amount?: number;
  percent?: number;
  amountInPercent?: boolean;
  amountTransactionMax?: number;
  amountTransactionMin?: number;
  priceCode?: string;
  serviceCode?: string;
  description?: string;
  modificationDate?: Moment;
  transactionId?: number;
}

export class TransactionPrice implements ITransactionPrice {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public amount?: number,
    public percent?: number,
    public amountInPercent?: boolean,
    public amountTransactionMax?: number,
    public amountTransactionMin?: number,
    public priceCode?: string,
    public serviceCode?: string,
    public description?: string,
    public modificationDate?: Moment,
    public transactionId?: number
  ) {
    this.amountInPercent = this.amountInPercent || false;
  }
}
