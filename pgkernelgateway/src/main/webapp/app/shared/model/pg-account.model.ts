import { Moment } from 'moment';

export interface IPgAccount {
  id?: number;
  number?: string;
  openingDate?: Moment;
  temporary?: boolean;
  closingDate?: Moment;
  imsi?: string;
  transactionCode?: string;
  validationDate?: Moment;
  accountStatusCode?: string;
  accountTypeCode?: string;
  customerCode?: string;
  currencyCode?: string;
  comment?: string;
  active?: boolean;
}

export class PgAccount implements IPgAccount {
  constructor(
    public id?: number,
    public number?: string,
    public openingDate?: Moment,
    public temporary?: boolean,
    public closingDate?: Moment,
    public imsi?: string,
    public transactionCode?: string,
    public validationDate?: Moment,
    public accountStatusCode?: string,
    public accountTypeCode?: string,
    public customerCode?: string,
    public currencyCode?: string,
    public comment?: string,
    public active?: boolean
  ) {
    this.temporary = this.temporary || false;
    this.active = this.active || false;
  }
}
