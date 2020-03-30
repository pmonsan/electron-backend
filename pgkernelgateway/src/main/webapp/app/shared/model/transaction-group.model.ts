export interface ITransactionGroup {
  id?: number;
  code?: string;
  label?: string;
  checkSubscription?: boolean;
  ignoreFees?: boolean;
  ignoreLimit?: boolean;
  ignoreCommission?: boolean;
  checkOtp?: boolean;
  active?: boolean;
}

export class TransactionGroup implements ITransactionGroup {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public checkSubscription?: boolean,
    public ignoreFees?: boolean,
    public ignoreLimit?: boolean,
    public ignoreCommission?: boolean,
    public checkOtp?: boolean,
    public active?: boolean
  ) {
    this.checkSubscription = this.checkSubscription || false;
    this.ignoreFees = this.ignoreFees || false;
    this.ignoreLimit = this.ignoreLimit || false;
    this.ignoreCommission = this.ignoreCommission || false;
    this.checkOtp = this.checkOtp || false;
    this.active = this.active || false;
  }
}
