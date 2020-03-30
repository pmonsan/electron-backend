export interface IQuestion {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class Question implements IQuestion {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
