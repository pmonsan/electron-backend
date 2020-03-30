import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  AgentTypeComponent,
  AgentTypeDetailComponent,
  AgentTypeUpdateComponent,
  AgentTypeDeletePopupComponent,
  AgentTypeDeleteDialogComponent,
  agentTypeRoute,
  agentTypePopupRoute
} from './';

const ENTITY_STATES = [...agentTypeRoute, ...agentTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgentTypeComponent,
    AgentTypeDetailComponent,
    AgentTypeUpdateComponent,
    AgentTypeDeleteDialogComponent,
    AgentTypeDeletePopupComponent
  ],
  entryComponents: [AgentTypeComponent, AgentTypeUpdateComponent, AgentTypeDeleteDialogComponent, AgentTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayAgentTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
