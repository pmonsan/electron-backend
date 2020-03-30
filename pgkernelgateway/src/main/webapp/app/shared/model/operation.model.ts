import { Moment } from 'moment';

export interface IOperation {
  id?: number;
  number?: string;
  amount?: number;
  direction?: string;
  operationDate?: Moment;
  accountNumber?: string;
  operationStatusCode?: string;
  operationTypeCode?: string;
  currencyCode?: string;
  userCode?: string;
  description?: string;
  active?: boolean;
  transactionId?: number;
}

export class Operation implements IOperation {
  constructor(
    public id?: number,
    public number?: string,
    public amount?: number,
    public direction?: string,
    public operationDate?: Moment,
    public accountNumber?: string,
    public operationStatusCode?: string,
    public operationTypeCode?: string,
    public currencyCode?: string,
    public userCode?: string,
    public description?: string,
    public active?: boolean,
    public transactionId?: number
  ) {
    this.active = this.active || false;
  }
}
