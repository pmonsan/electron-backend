/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelDetailComponent } from 'app/entities/pg-channel/pg-channel-detail.component';
import { PgChannel } from 'app/shared/model/pg-channel.model';

describe('Component Tests', () => {
  describe('PgChannel Management Detail Component', () => {
    let comp: PgChannelDetailComponent;
    let fixture: ComponentFixture<PgChannelDetailComponent>;
    const route = ({ data: of({ pgChannel: new PgChannel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgChannelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgChannelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgChannel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
