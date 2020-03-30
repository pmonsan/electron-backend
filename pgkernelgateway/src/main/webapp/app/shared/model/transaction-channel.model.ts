export interface ITransactionChannel {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class TransactionChannel implements ITransactionChannel {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
