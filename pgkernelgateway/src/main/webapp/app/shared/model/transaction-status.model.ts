export interface ITransactionStatus {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class TransactionStatus implements ITransactionStatus {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
