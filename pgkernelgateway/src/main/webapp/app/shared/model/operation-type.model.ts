export interface IOperationType {
  id?: number;
  code?: string;
  active?: boolean;
  label?: string;
}

export class OperationType implements IOperationType {
  constructor(public id?: number, public code?: string, public active?: boolean, public label?: string) {
    this.active = this.active || false;
  }
}
