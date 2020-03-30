import { Moment } from 'moment';

export interface IPg8583Request {
  id?: number;
  number?: string;
  apiKey?: string;
  encryptedData?: string;
  decryptedData?: string;
  registrationDate?: Moment;
  responseDate?: Moment;
  requestResponse?: string;
  status?: string;
  reason?: string;
  pgapsMessage?: string;
  pgapsTransactionNumber?: string;
  active?: boolean;
}

export class Pg8583Request implements IPg8583Request {
  constructor(
    public id?: number,
    public number?: string,
    public apiKey?: string,
    public encryptedData?: string,
    public decryptedData?: string,
    public registrationDate?: Moment,
    public responseDate?: Moment,
    public requestResponse?: string,
    public status?: string,
    public reason?: string,
    public pgapsMessage?: string,
    public pgapsTransactionNumber?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
