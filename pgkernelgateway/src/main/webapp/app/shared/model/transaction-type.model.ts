export interface ITransactionType {
  id?: number;
  code?: string;
  label?: string;
  useTransactionGroup?: boolean;
  checkSubscription?: boolean;
  ignoreFees?: boolean;
  ignoreLimit?: boolean;
  ignoreCommission?: boolean;
  checkOtp?: boolean;
  pgMessageModelCode?: string;
  transactionGroupCode?: string;
  active?: boolean;
}

export class TransactionType implements ITransactionType {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public useTransactionGroup?: boolean,
    public checkSubscription?: boolean,
    public ignoreFees?: boolean,
    public ignoreLimit?: boolean,
    public ignoreCommission?: boolean,
    public checkOtp?: boolean,
    public pgMessageModelCode?: string,
    public transactionGroupCode?: string,
    public active?: boolean
  ) {
    this.useTransactionGroup = this.useTransactionGroup || false;
    this.checkSubscription = this.checkSubscription || false;
    this.ignoreFees = this.ignoreFees || false;
    this.ignoreLimit = this.ignoreLimit || false;
    this.ignoreCommission = this.ignoreCommission || false;
    this.checkOtp = this.checkOtp || false;
    this.active = this.active || false;
  }
}
