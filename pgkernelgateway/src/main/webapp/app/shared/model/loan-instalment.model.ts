import { Moment } from 'moment';

export interface ILoanInstalment {
  id?: number;
  code?: string;
  expectedPaymentDate?: Moment;
  realPaymentDate?: Moment;
  amountToPay?: number;
  penalityFee?: number;
  statusDate?: Moment;
  loanInstalmentStatusCode?: string;
  active?: boolean;
  loanId?: number;
}

export class LoanInstalment implements ILoanInstalment {
  constructor(
    public id?: number,
    public code?: string,
    public expectedPaymentDate?: Moment,
    public realPaymentDate?: Moment,
    public amountToPay?: number,
    public penalityFee?: number,
    public statusDate?: Moment,
    public loanInstalmentStatusCode?: string,
    public active?: boolean,
    public loanId?: number
  ) {
    this.active = this.active || false;
  }
}
