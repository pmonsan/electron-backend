export interface ITransactionInfo {
  id?: number;
  transactionPropertyCode?: string;
  value?: string;
  active?: boolean;
  transactionId?: number;
}

export class TransactionInfo implements ITransactionInfo {
  constructor(
    public id?: number,
    public transactionPropertyCode?: string,
    public value?: string,
    public active?: boolean,
    public transactionId?: number
  ) {
    this.active = this.active || false;
  }
}
