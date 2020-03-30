import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgent } from 'app/shared/model/agent.model';
import { AccountService } from 'app/core';
import { AgentService } from './agent.service';

@Component({
  selector: 'jhi-agent',
  templateUrl: './agent.component.html'
})
export class AgentComponent implements OnInit, OnDestroy {
  agents: IAgent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected agentService: AgentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.agentService
      .query()
      .pipe(
        filter((res: HttpResponse<IAgent[]>) => res.ok),
        map((res: HttpResponse<IAgent[]>) => res.body)
      )
      .subscribe(
        (res: IAgent[]) => {
          this.agents = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAgents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgent) {
    return item.id;
  }

  registerChangeInAgents() {
    this.eventSubscriber = this.eventManager.subscribe('agentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
