import { Moment } from 'moment';

export interface ITransactionCommission {
  id?: number;
  code?: string;
  label?: string;
  amount?: number;
  amountInPercent?: boolean;
  dateCreation?: Moment;
  percent?: number;
  currencyCode?: string;
  partnerCode?: string;
  serviceCode?: string;
  description?: string;
  commission?: number;
  transactionId?: number;
}

export class TransactionCommission implements ITransactionCommission {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public amount?: number,
    public amountInPercent?: boolean,
    public dateCreation?: Moment,
    public percent?: number,
    public currencyCode?: string,
    public partnerCode?: string,
    public serviceCode?: string,
    public description?: string,
    public commission?: number,
    public transactionId?: number
  ) {
    this.amountInPercent = this.amountInPercent || false;
  }
}
