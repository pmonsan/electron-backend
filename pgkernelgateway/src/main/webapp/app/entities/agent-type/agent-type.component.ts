import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgentType } from 'app/shared/model/agent-type.model';
import { AccountService } from 'app/core';
import { AgentTypeService } from './agent-type.service';

@Component({
  selector: 'jhi-agent-type',
  templateUrl: './agent-type.component.html'
})
export class AgentTypeComponent implements OnInit, OnDestroy {
  agentTypes: IAgentType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agentTypeService: AgentTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agentTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgentType[]>) => res.ok),
        map((res: HttpResponse<IAgentType[]>) => res.body)
      )
      .subscribe(
        (res: IAgentType[]) => {
          this.agentTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgentTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgentType) {
    return item.id;
  }

  registerChangeInAgentTypes() {
    this.eventSubscriber = this.eventManager.subscribe('agentTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
