import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  transactionNumber?: string;
  label?: string;
  transactionAmount?: number;
  transactionDate?: Moment;
  feesSupported?: boolean;
  transactionFees?: number;
  priceCode?: string;
  fromPartnerCode?: string;
  toPartnerCode?: string;
  transactionStatusCode?: string;
  transactionTypeCode?: string;
  serviceCode?: string;
  comment?: string;
  active?: boolean;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public transactionNumber?: string,
    public label?: string,
    public transactionAmount?: number,
    public transactionDate?: Moment,
    public feesSupported?: boolean,
    public transactionFees?: number,
    public priceCode?: string,
    public fromPartnerCode?: string,
    public toPartnerCode?: string,
    public transactionStatusCode?: string,
    public transactionTypeCode?: string,
    public serviceCode?: string,
    public comment?: string,
    public active?: boolean
  ) {
    this.feesSupported = this.feesSupported || false;
    this.active = this.active || false;
  }
}
