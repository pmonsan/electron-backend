import { Moment } from 'moment';

export interface IInternalConnectorRequest {
  id?: number;
  number?: string;
  module?: string;
  connector?: string;
  connectorType?: string;
  requestData?: string;
  registrationDate?: Moment;
  pgapsTransactionNumber?: string;
  serviceId?: string;
  accountNumber?: string;
  amount?: number;
  balance?: number;
  accountValidation?: boolean;
  numberOfTransactions?: number;
  lastTransactions?: string;
  action?: string;
  responseDate?: Moment;
  status?: string;
  reason?: string;
  partnerTransactionNumber?: string;
  active?: boolean;
}

export class InternalConnectorRequest implements IInternalConnectorRequest {
  constructor(
    public id?: number,
    public number?: string,
    public module?: string,
    public connector?: string,
    public connectorType?: string,
    public requestData?: string,
    public registrationDate?: Moment,
    public pgapsTransactionNumber?: string,
    public serviceId?: string,
    public accountNumber?: string,
    public amount?: number,
    public balance?: number,
    public accountValidation?: boolean,
    public numberOfTransactions?: number,
    public lastTransactions?: string,
    public action?: string,
    public responseDate?: Moment,
    public status?: string,
    public reason?: string,
    public partnerTransactionNumber?: string,
    public active?: boolean
  ) {
    this.accountValidation = this.accountValidation || false;
    this.active = this.active || false;
  }
}
