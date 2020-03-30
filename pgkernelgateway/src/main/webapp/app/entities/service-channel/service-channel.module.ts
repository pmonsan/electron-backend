import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ServiceChannelComponent,
  ServiceChannelDetailComponent,
  ServiceChannelUpdateComponent,
  ServiceChannelDeletePopupComponent,
  ServiceChannelDeleteDialogComponent,
  serviceChannelRoute,
  serviceChannelPopupRoute
} from './';

const ENTITY_STATES = [...serviceChannelRoute, ...serviceChannelPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceChannelComponent,
    ServiceChannelDetailComponent,
    ServiceChannelUpdateComponent,
    ServiceChannelDeleteDialogComponent,
    ServiceChannelDeletePopupComponent
  ],
  entryComponents: [
    ServiceChannelComponent,
    ServiceChannelUpdateComponent,
    ServiceChannelDeleteDialogComponent,
    ServiceChannelDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayServiceChannelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
