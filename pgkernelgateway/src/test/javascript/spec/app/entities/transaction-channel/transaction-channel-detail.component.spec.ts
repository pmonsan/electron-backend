/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionChannelDetailComponent } from 'app/entities/transaction-channel/transaction-channel-detail.component';
import { TransactionChannel } from 'app/shared/model/transaction-channel.model';

describe('Component Tests', () => {
  describe('TransactionChannel Management Detail Component', () => {
    let comp: TransactionChannelDetailComponent;
    let fixture: ComponentFixture<TransactionChannelDetailComponent>;
    const route = ({ data: of({ transactionChannel: new TransactionChannel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionChannelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionChannelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionChannelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionChannel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
