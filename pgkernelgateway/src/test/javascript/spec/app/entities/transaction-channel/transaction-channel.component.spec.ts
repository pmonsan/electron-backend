/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionChannelComponent } from 'app/entities/transaction-channel/transaction-channel.component';
import { TransactionChannelService } from 'app/entities/transaction-channel/transaction-channel.service';
import { TransactionChannel } from 'app/shared/model/transaction-channel.model';

describe('Component Tests', () => {
  describe('TransactionChannel Management Component', () => {
    let comp: TransactionChannelComponent;
    let fixture: ComponentFixture<TransactionChannelComponent>;
    let service: TransactionChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionChannelComponent],
        providers: []
      })
        .overrideTemplate(TransactionChannelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionChannelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionChannelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionChannel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionChannels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
